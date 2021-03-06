<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="center-align">
    <h5 style="color: orange">In your cart :</h5>
</div>
<c:if test="${not empty requestScope.cart}">
    <c:forEach var="cartLine" items="${requestScope.cart.productCards}">
        <div class="row">
            <div>
                <div class="col s8 left">
                    <a href="<c:url value='/product/${cartLine.productId}'/>">
                        ${cartLine.productName}
                    </a>
                </div>
                <div class="col s4 right-align">
                        ${cartLine.productPrice}&#8364
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col s6 left">
                <form action="<c:url value='/cart/addRemove' />" method="post">
                    <div style="display: inline-block">
                        <input type="hidden" name="productId" value="${cartLine.productId}">
                        <input type="submit" name="remove" value="-">
                    </div>
                    <div style="display: inline-block">
                        <input type="hidden" name="productId" value="${cartLine.productId}">
                        <input type="submit" name="add" value="+">
                    </div>
                </form>
            </div>
            <div class="col s6 right-align">
                    ${cartLine.quantity}
                <small>items</small>
            </div>
        </div>
    </c:forEach>
</c:if>

<div class="row">
    <div class="col s6 left-align">
        Total :
    </div>
    <div class="col s6 right-align">
        <c:if test="${empty requestScope.cart.totalPrice}">
            0 &#8364
        </c:if>
        <c:if test="${not empty requestScope.cart.totalPrice}">
            ${requestScope.cart.totalPrice}&#8364
        </c:if>
    </div>
</div>
<hr>

<a href="<c:url value='/checkout'/>">
    <button class="btn waves-effect waves-light blue" type="submit" name="buy">Buy
        <i class="material-icons right">payment</i>
    </button>
</a>
