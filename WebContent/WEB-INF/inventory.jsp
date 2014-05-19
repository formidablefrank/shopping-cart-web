<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Buy From Us | Admin Dashboard</title>

    <!-- Bootstrap core CSS -->
    <link href="assets/bootstrap.css" rel="stylesheet">

    <!-- Add custom CSS here -->
    <link href="assets/sb-admin.css" rel="stylesheet">
    <link rel="stylesheet" href="assets/font-awesome.css">
  </head>

  <body>

    <div id="wrapper">

      <!-- Sidebar -->
      <nav class="navbar navbar-inverse navbar-fixed-top">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="admin">Admin Page</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
          <ul class="nav navbar-nav side-nav">
            <li><a href="admin"><i class="fa fa-dashboard"></i> Dashboard</a></li>
            <li><a href="categories"><i class="fa fa-table"></i> Categories</a></li>
            <li class="active"><a href="inventory"><i class="fa fa-table"></i> Inventory</a></li>
            <li><a href="addCategory"><i class="fa fa-edit"></i> Add category</a></li>
            <li><a href="addProduct"><i class="fa fa-edit"></i> Add product</a></li>
          </ul>

          <ul class="nav navbar-nav navbar-right navbar-user">
            <li class="dropdown user-dropdown">
              <a href="" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> ${sessionScope.username}</a>
            </li>
            <li class="dropdown user-dropdown"><a href="home">Home Page	</a></li>
            <li class="dropdown user-dropdown"><a href="logout">Log Out</a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </nav>

      <div id="page-wrapper">

        <div class="row">
          <div class="col-lg-12">
            <h1>Available Products</h1>
            <ol class="breadcrumb">
              <li><a href="admin"><i class="fa fa-dashboard"></i> Dashboard</a></li>
              <li class="active"><i class="fa fa-table"></i> Categories</li>
            </ol>
          </div>
        </div><!-- /.row -->
        
        <c:forEach items="${inventory}" var="category">
        <div class="row">
          <div class="col-lg-12">
            <h2>${category.getName()}</h2>
            <div class="table-responsive">
              <table class="table table-hover table-bordered table-striped tablesorter">
                <thead>
                  <tr>
                    <th>Name <i class="fa fa-sort"></i></th>
                    <th>Price <i class="fa fa-sort"></i></th>
                    <th>Quantity <i class="fa fa-sort"></i></th>
                    <th>Image</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${category.getList().keySet()}" var="product">
                  	<tr>
                      <td>${product.getName()}</td>
                      <td>Php ${product.getPrice()}</td>
                      <td>${category.getList().get(product)}</td>
                      <td><img src="${product.getImage()}" width="50" height="50"></img></td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div><!-- /.row -->
        
        </c:forEach>
        
        <c:if test="${errorMsg != null}">
        <div class="row">
          <div class="col-lg-6">
            <div class="alert alert-dismissable alert-danger">
              <button type="button" class="close" data-dismiss="alert">&times;</button>
              <strong>Oh snap!</strong> ${errorMsg}
            </div>
          </div>
        </div>
        </c:if>

      </div><!-- /#page-wrapper -->

    </div><!-- /#wrapper -->
    

    <!-- JavaScript -->
    <script src="assets/jquery-1.10.2.js"></script>
    <script src="assets/bootstrap.js"></script>

    <!-- Page Specific Plugins -->
    <script src="assets/tablesorter/jquery.tablesorter.js"></script>
    <script src="assets/tablesorter/tables.js"></script>

  </body>
</html>