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
    <script src="../lib/j.js"></script>
    <script src="../lib/b.js"></script>
    <link href="../lib/b.css" rel="stylesheet">


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
                    <form:form role="form" modelAttribute="registerModel" action="Register" method="post">
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="text" name="Name" id="first_name" class="form-control input-sm"
                                           placeholder="First Name">
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="text" name="Surname" id="last_name" class="form-control input-sm"
                                           placeholder="Last Name">
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <input type="text" name="Email" id="email" class="form-control input-sm"
                                   placeholder="Email Address">
                        </div>

                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="password" name="Password" id="password"
                                           class="form-control input-sm" placeholder="Password">
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="password" name="ConfirmPassword" id="ConfirmPassword"
                                           class="form-control input-sm" placeholder="Confirm Password">
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <label>City</label>
                                    <select id="City" name="CityId" class="form-control" onchange="citiesChange()"
                                            required>
                                        <option selected disabled>Select City</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <label>City</label>
                                    <select id="State" name="StateId" class="form-control" required>
                                        <option selected disabled>Select State</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <input type="submit" value="Register" class="btn btn-info btn-block">

                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function citiesChange() {
        var city = $('#City');
        $.ajax({
            url: "/Api/Cities",
            type: "get",
            data: {},
            success: function (data) {
                $.each(JSON.parse(data),
                    function (index, row) {
                        city.append("<option value='" + row.Id + "'>" + row.Name + "</option>");
                    });
            }
        });

    });

    function citiesChange() {
        var country = $('#City');
        var state = $('#State');
        $.ajax({
            url: "/Api/States",
            type: "get",
            data: {id: country.val()},
            success: function (data) {
                state.empty();
                $.each(JSON.parse(data),
                    function (index, row) {
                        state.append("<option value='" + row[0] + "'>" + row[1] + "</option>");
                    });
            }
        });

    }

</script>
</body>
</html>
