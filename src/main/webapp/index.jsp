<html>
<body>
    <h2>REST API Web Application for String Operation!</h2>
    <table border="1">
	  <tr>
	    <th>Operation</th>
	    <th>REST URI</th>
	    <th>Method</th>
	  </tr>
	  <tr>
	    <td>List all uploaded string sets</td>
	    <td><a href="rest/set/">rest/set/</a></td>
	    <td>GET</td>
	  </tr>
	  <tr>
	    <td>View the strings in a given set</td>
	    <td><a href="rest/set/1">rest/set/{id - 1}</a></td>
	    <td>GET</td>
	  </tr>
	  <tr>
	    <td>Upload a set of strings</td>
	    <td><a href="rest/set/upload?strings={hij}&strings={jkl}">rest/set/upload?strings={hij}&strings={jkl}</a></td>
	    <td>POST</td>
	  </tr>
	  <tr>
	    <td>Search a given string in the uploaded sets</td>
	    <td><a href="rest/set/search?searchString={hij}">rest/set/search?searchString={hij}</a></td>
	    <td>POST</td>
	  </tr>
	  <tr>
	    <td>Delete a given set of strings by giving its id</td>
	    <td><a href="rest/set/{id}/delete">rest/set/{id-1}/delete</a></td>
	    <td>GET</td>
	  </tr>
	  <tr>
	    <td>Get the statistics a given string set by giving its id</td>
	    <td><a href="rest/set/{id}/set_statistic">rest/set/{id}/set_statistic</a></td>
	    <td>GET</td>
	  </tr>
	  <tr>
	    <td>Find a string which belongs to the largest number of sets</td>
	    <td><a href="rest/set/most_common">rest/set/most_common</a></td>
	    <td>GET</td>
	  </tr>
	  <tr>
	    <td>Find the longest string in all the sets</td>
	    <td><a href="rest/set/longest">rest/set/longest</a></td>
	    <td>GET</td>
	  </tr>
	  <tr>
	    <td>Find the string which belongs to exactly given number of sets</td>
	    <td><a href="rest/set/exactly_in?count={3}">rest/set/exactly_in?count={count}</a></td>
	    <td>POST</td>
	  </tr>
	  <tr>
	    <td>Create a new set which is an intersection of two previously uploaded sets</td>
	    <td><a href="rest/set/{1}/{2}/create_intersection">rest/set/{id-1}/{idb-2}/create_intersection</a></td>
	    <td>GET</td>
	  </tr>
	</table>    
</body>
</html>