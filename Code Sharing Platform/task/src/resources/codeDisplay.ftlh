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
<#if code.views gt 0>
    <span>${code.views} more views allowed</span>
</#if>
<br>
<#if code.time gt 0>
    <span>The code will be available for ${code.time} seconds</span>
</#if>
<br>
<div class="code_block">
    <pre id="code_snippet"><code>${code.code}</code></pre>
</div>

</body>
</html>