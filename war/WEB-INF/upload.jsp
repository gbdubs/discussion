<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Upload a JSON Definition for a new discussion</title>
</head>
<body>
	<form method="post" action="/upload">
		<label for="discussion-name">Discussion Name</label>
		<input type="text" name="discussion-name"/>
		<label for="discussion-name">Starting Snippet Number</label>
		<input type="number" name="start-snippet"/>
		<label for="discussion-snippets">Discussion Snippets</label>
		<textarea name="discussion-snippets"></textarea>
		<button type="submit">Submit</button>
	</form>
</body>
</html>