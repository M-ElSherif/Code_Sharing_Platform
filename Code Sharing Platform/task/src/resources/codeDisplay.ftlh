<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Code</title>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>

    <style>
        #load_date {
            color: green;
        }

        .code_block {
            width: 75%;
            border-style: solid;
            border-width: 1px;
            background: lightgray;
        }

        #code_snippet {
            word-wrap: normal;
        }
    </style>
</head>
<body>

<code><span id="load_date">${code.date}</span></code>
<br>
<#if code.views gte 0 && code.viewRestricted>
    <span id="views_restriction">${code.views} more views allowed</span>
</#if>
<br>
<#if code.time gte 0 && code.timeRestricted>
    <span id="time_restriction">The code will be available for ${code.time} seconds</span>
</#if>
<br>
<pre id="code_snippet"><code id="code_snippet">${code.code}</code></pre>

</body>
</html>