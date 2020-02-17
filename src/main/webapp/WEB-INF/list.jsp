<%@ page language="java" contentType="text/html; utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Shopping list demo app</title>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<!-- simple classless CSS stylesheet, see: https://github.com/oxalorg/sakura -->
<link rel="stylesheet" href="https://unpkg.com/sakura.css/css/sakura.css" type="text/css">
<script src="/scripts/app.js"></script>
</head>

<body>
	<h2>📝 Shopping List Example</h2>

	<form id="add-new-form" method="post">
		<input id="new-item-title" name="title" required type="text"
			placeholder=" type item here..." autofocus /> <input type="submit"
			id="add-new-item" value="Add to list" />
	</form>

	<table>
		<thead>
			<tr>
				<th>Title</th>
				<th></th>
			</tr>
		</thead>
		<tbody id="list-items">
			<c:forEach items="${ allItems }" var="item">
				<tr id="product-${ item.getId() }">
					<td class="title"><c:out value="${ item.getTitle() }" /></td>
					<td><button class="remove" onclick="removeProduct(${ item.getId() })">&times;</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>