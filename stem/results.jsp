<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, javax.servlet.*, joarLib.Joar_DB, joarLib.Siteword, joarLib.Sites"%>

<%@ include file="header.jsp" %>

<%
  String word = request.getParameter("word");
  word = new String(word.getBytes("UTF-8"));

  Joar_DB database = new Joar_DB();
  database.open();
  List<Siteword> sitewords = database.findWord(word);
  Sites site2 = null;

  if (Boolean.FALSE.equals(database.validTrending(word))) {
    database.importTrending(word);
  } else {
    database.updateTrending(word);
  }

%>
  <div class="container theme-showcase" role="main">
    <img class="img-responsive img-rounded center-block" src="../joar/logojoar.PNG" alt="j0ar Logo">
    <br>
    <h4>Αποτελέσματα αναζήτησης για: <b><%=word%></b></h4>
    <%
    if (!sitewords.isEmpty()) {
      for (Siteword siteword : sitewords) {
        site2 = database.findSite(siteword.getSite());
        %>
          <h3><a href="<%=site2.getSite()%>"><%=site2.getTitle()%></a></h3>
          <p>
            <% if (site2.getSite() != null) {%>
              <span style="color:green;"><%=site2.getSite()%></span>
            <%}%>

            <% if (site2.getDescription() != null) {%>
              - <%=site2.getDescription()%>
            <%}%>
          </p>
      <%}
    } else {%>
      <div class='alert alert-warning' role='alert' style='text-align:center'>
        Δεν βρέθηκαν αποτελέσματα - Κάντε αναζήτηση <a href="http://ism.dmst.aueb.gr/ismgroup3/joar/stem.jsp?word=<%=word%>">Stem</a>
      </div>
    <%}
      database.close();%>
  </div> <!-- /container -->

<%@ include file="footer.jsp" %>
