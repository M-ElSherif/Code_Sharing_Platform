<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Latest</title>
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

<#list codeList as code>
    <code><span id="load_date">${code.date}</span></code>
    <div class="code_block">
        <pre id="code_snippet"><code>${code.code}</code></pre>
    </div>
</#list>

</body>
</html>