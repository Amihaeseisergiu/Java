<html>
<head>
  <title>Welcome!</title>
</head>
<body>
  <h1>List of Documents in Catalog ${catalog}:</h1>
  <p>Documents:
  <#list convertedMap as item>
	<#assign myObjectKey = item.getKey()/>
	<#assign myObjectValue = item.getValue()/>
	${myObjectKey.name} as ${myObjectKey.id} at ${myObjectKey.location}
	<#list myObjectValue as itemKey, itemValue>
		<p>${itemKey} : ${itemValue}
	</#list>
  </#list>
</body>
</html>