<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>{{ config('l5-swagger.title') }}</title>
    <link href="https://cdn.jsdelivr.net/npm/swagger-ui-dist@4/swagger-ui.css" type="text/css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/swagger-ui-dist@4/swagger-ui-bundle.js"></script>
</head>
<body>
    <div id="swagger-ui"></div>
    <script>
        window.onload = function() {
            fetch("{{ config('l5-swagger.path') }}?format=json")
                .then(response => response.json())
                .then(data => {
                    SwaggerUIBundle({
                        url: data.url,
                        dom_id: '#swagger-ui',
                        layout: {"adminApiUrl": "/api/documentation"},
                        presets: [
                            SwaggerUIBundle.presets.apis,
                            SwaggerUIBundle.presets.response_type_defaults,
                        ],
                        plugins: [SwaggerUIBundle.plugins.DownloadUrl],
                    })
                });
        };
    </script>
</body>
</html>