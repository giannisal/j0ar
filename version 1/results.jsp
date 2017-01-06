<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,   javax.servlet.*, joarLib.*"%>

<%@ include file="header.jsp" %>

<%
 	String word = request.getParameter("word");
	word = new String(word.getBytes("ISO-8859-1"), "ISO-8859-7");
	
	String[] words;
	words = word.split(" ");

    int n = words.length;
	
	String cond;
	if(n==1) cond = "word=?";
    else  cond = "word=? OR ";
	
    for(int i = 2;i<=n;i++)	{
		if(i==n) 
			cond += "word=?";
		else
			cond += "word=? OR ";
	}
	
	String query ="Select count(site) as countSites,sum(frequency) as countSumFrequency,site "+
			                  "from joar_siteword " +  
							  "where " + cond + " group by site order by countSites DESC, countSumFrequency DESC";
	
	Joar_DB database = new Joar_DB();
	database.open();
    List<Siteword> sitewords = database.getSites(query,n,words);

  if (Boolean.FALSE.equals(database.validTrending(word))) {
    database.importTrending(word);
  } else {
    database.updateTrending(word);
  }

  database.close();
%>
  <div class="container theme-showcase" role="main">
    <img class="img-responsive img-rounded center-block" src="../joar/logojoar.PNG" alt="j0ar Logo">
    <br>
    <h4>Αποτελέσματα αναζήτησης για: <b><%=word%></b></h4>
    <%
    if (!sitewords.isEmpty()) {
      for (Siteword siteword : sitewords) {%>
        <div class='alert alert-success' role='alert' style='text-align:center'>
          <a href="<%=siteword.getSite()%>"><%=siteword.getSite()%></a>
        </div>
      <%}
    } else {%>
      <div class='alert alert-warning' role='alert' style='text-align:center'>
        Δεν βρέθηκαν αποτελέσματα - Επιστρέψτε σύντομα
      </div>
    <%}%>
  </div> <!-- /container -->

<%@ include file="footer.jsp" %>
