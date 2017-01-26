<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de Livros</title>
</head>
<body>

	<table>
		<tr>
			<th><a
				href="${spring:mvcUrl(PC#show).arg(0,product.id).buidl()}">${product.title}</a></th>
			<th>Valores</th>
		</tr>
		<c:forEach items="${products}" var="product">
			<tr>
				<td><a href="#">${product.title}</a></td>
				<td><c:forEach items="${product.prices}" var="price">
						[${price.value} - ${price.bookType}]
					</c:forEach></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>