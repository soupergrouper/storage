<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'item.label', default: 'Item')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <export:resource />
    </head>
    <body>
        <a href="#list-item" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li><g:link class="list" action="lowStock"><g:message code="button.label.lowStock" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div class="nav">
            <fieldset class="form">
                <g:form action="list" method="GET">
                    <div class="fieldcontain">
                        <label for="query">Search for items:</label>
                        <g:textField name="query" value="${params.query}"/>
                    </div>
                </g:form>
            </fieldset>
        </div>
        <div id="list-item" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${itemList}" />

            <div class="pagination">
                <g:paginate total="${itemCount ?: 0}" />
            </div>
            <export:formats formats="['csv', 'excel']" action="export" />
        </div>
    </body>
</html>