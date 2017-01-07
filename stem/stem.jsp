<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, javax.servlet.*, joarLib.Joar_DB, joarLib.Siteword"%>

<%@ include file="header.jsp" %>

<%
  String word = request.getParameter("word");
  word = new String(word.getBytes("UTF-8"));

  boolean findSuffix = false;
  boolean findPrefix = false;
  String [] suffixes1 = {"s"};
  String [] suffixes2 = {"es", "ly", "er", "or", "al", "ty",  "ic", "en", "er", "ed"};
  String [] suffixes3 = {"ing", "ion", "ian", "ial", "ity", "oid", "ose", "ous", "ive",  "ful", "est", "ade", "age", "dom", "ify", "ism"};
  String [] suffixes4 = {"tion", "most", "word", "ware", "wise", "uous", "ure",  "ation", "ition", "ling", "itis", "ible", "able", "ness", "some", "ment", "ous", "ical", "eous", "ious", "less", "ship", "ance", "ence","ancy", "ency", "like", "hood"};
  String [] suffixes5 = {"ative", "itive", "acious", "arian", "esque", "ulent"};
  String [] suffixesmore = {"phobia", "ization"};

  String [] prefixes4 = {"over"};



  int len = word.length();

  String preword = "";
  String sufword = "";


if (word.length()>=4) {
  for (int i=0; i<prefixes4.length; i++) {
    if (word.substring(0, 4).equals(prefixes4[i])) {
      preword = word.substring(0, 4);
      word = word.substring(4, len);
      findPrefix = true;
      break;
    }
  }
}

  len = word.length();
if (word.length()>=5) {
  for (int i = 0; i < suffixes5.length; i++) {
    if(word.substring(len - 5, len).equals(suffixes5[i])){
      sufword = word.substring(len - 5, len);
      word = word.substring(0,len - 5);
      findSuffix = true;
      break;
    }
  }
}
  if(findSuffix==false && word.length()>=4) {
    for (int i = 0; i < suffixes4.length; i++) {
      if(word.substring(len - 4, len).equals(suffixes4[i])){
        sufword = word.substring(len - 4, len);
        word = word.substring(0,len - 4);
        findSuffix = true;
        break;
      }
    }
  }

  if(findSuffix==false && word.length()>=3) {
    for (int i = 0; i < suffixes3.length; i++) {
      if(word.substring(len - 3, len).equals(suffixes3[i])){
        sufword = word.substring(len - 3, len);
        word = word.substring(0,len - 3);
        findSuffix = true;
        break;
      }
    }
  }

  if(findSuffix==false &&  word.length()>=2) {
    for (int i = 0; i < suffixes2.length; i++) {
      if(word.substring(len - 2, len).equals(suffixes2[i])){
        sufword = word.substring(len -2, len);
        word = word.substring(0,len - 2);
        findSuffix = true;
        break;
      }
    }
  }

  if(findSuffix==false && word.length()>=1) {
    for (int i = 0; i < suffixes1.length; i++) {
      if(word.substring(len - 1, len).equals(suffixes1[i])){
        sufword = word.substring(1, len);
        word = word.substring(0,len - 1);
        findSuffix = true;
        break;
      }
    }
  }

  // if (word.substring(len-2, len).equals("ed")) {
  //   word = word.substring(0,len -2);
  // }

  Joar_DB database = new Joar_DB();
  database.open();

  List<Siteword> sitewords = database.findWord(word);

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
    <h4>Αποτελέσματα αναζήτησης για: <b><%=preword%><span style="color:red;"><%=word%></span><%=sufword%></b></h4>
    <%-- <%=word.substring(len-2, len-1)%> --%>
    <%
    if (!sitewords.isEmpty()) {
      for (Siteword siteword : sitewords) {%>
        <div class='alert alert-success' role='alert' style='text-align:center'>
          <b>[<%=siteword.getWord()%>]</b>
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
