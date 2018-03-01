<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: OMANSAK
  Date: 8.09.2017
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <script src="../../lib/j.js"></script>
    <script src="../../lib/b.js"></script>
    <link href="../../lib/b.css" rel="stylesheet">

</head>
<style>
    body {
        background-color: #525252;
    }

    .centered-form {
        margin-top: 60px;
    }

    .centered-form .panel {
        background: rgba(255, 255, 255, 0.8);
        box-shadow: rgba(0, 0, 0, 0.3) 20px 20px 20px;
    }
</style>
<body>

<div class="container">
    <div class="row centered-form">
        <div class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please sign up for Bootsnipp
                        <small>It's free!</small>
                    </h3>
                </div>
                <div class="panel-body">
                    <c:if test="${msg !=null}">
                        <div class="alert ${alert}">
                                ${msg}
                        </div>
                    </c:if>
                    <form:form role="form" action="ForgotPassword/Post" method="get">
                        <h5>Your Email for Forgot Password link</h5>
                        <div class="row">
                            <div class="col-md-1"></div>
                            <div class="col-md-10">
                                <div class="form-group">
                                    <input type="text" name="email" class="form-control input-sm"
                                           placeholder="Email">
                                </div>
                            </div>
                            <div class="col-md-1"></div>
                        </div>

                        <input type="submit" value="Send Activation" class="btn btn-info btn-block">

                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
