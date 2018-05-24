<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TPO_03</title>
    <style>
        body {
            margin-top: 10%;
        }
    </style>
</head>
<body>
<div style="text-align: center;">
    <%--<h1><span style="color: #b0ff00;">GET</span></h1>--%>
    <h1><span style="color: black;">GET</span></h1>
    <form action="addValues" method="get">
        <input type="text" placeholder="First parameter" name="1"><br>
        <input type="text" placeholder="Second parameter" name="2"><br>
        <input type="submit" value="Submit"><br>
    </form>
    <%--<h1><span style="color: #b0ff00;">POST</span></h1>--%>
    <h1><span style="color: black;">POST</span></h1>
    <form action="addValues" method="post">
        <input type="text" placeholder="First parameter" name="1"><br>
        <input type="text" placeholder="Second parameter" name="2"><br>
        <input type="submit" value="Submit"><br>
    </form>
</div>
</body>
</html>
