<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, javax.servlet.*, joarLib.Joar_DB, joarLib.Siteword, joarLib.Trending"%>

<%@ include file="header.jsp" %>

<div class="container theme-showcase" role="main">

  <%
    Joar_DB database = new Joar_DB();
    database.open();
    List<Trending> trendings = database.findTrending();
    database.close();
  %>

  <div class='alert alert-info' role='alert'> <b>Trending: </b>
    <%
    for (Trending trending : trendings) {%>
      <a href="http://ism.dmst.aueb.gr/ismgroup3/joar/results.jsp?word=<%=trending.getWord()%>"><%=trending.getWord()%></a>,
    <%}
    %>
  </div>

  <img class="img-responsive img-rounded center-block" src="../joar/logojoar.PNG" alt="j0ar Logo">
  <form  method = 'get' action = 'results.jsp'>
    <div class="form-group">
    <input type="text" class="form-control" id="word" name="word"/>

  	<script>
      $("#word").autocomplete("getdata.jsp", {maxItemsToShow : 5});
  	</script>
    </div>
    <button type="submit" class="btn btn-primary">Αναζήτηση</button>
    <button type="submit" formaction="stem.jsp" class="btn btn-primary">Stem</button>
    <button type="submit" formaction="lucky.jsp" class="btn btn-primary">Αισθάνομαι Τυχερός</button>
  </form>

  <br>
  <br>
</div> <!-- /container -->

</body>
</html>

<%-- <%@ include file="footer.jsp" %> --%>
