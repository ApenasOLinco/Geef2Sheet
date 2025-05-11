curl -X POST \
-O \
localhost:8080/gif/convert \
-F gif=@"./patowalk.gif" \
-F 'configurations=@configuration.json;type=application/json'
