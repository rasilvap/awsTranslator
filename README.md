# awsTranslator
Translator app using the Aws machine translation Service

### How to run

mvn package

run the DemoApplication class

#### ConfigureIAM Identity Center authentication

follow the next guide: https://docs.aws.amazon.com/sdkref/latest/guide/access-sso.html#idcGettingStarted



### Input and Output examples:

You can use the next body in Postman, pointing to the Url: http://localhost:8080/translator

#### Input

```json
{
"source": "en",
"target": "es",
"text": "Hello world"
}
```

#### Output

```json
{
"source": "en",
"target": "es",
"answer": "Hola mundo",
"timestamp": "2023-09-13T20:46:34.698+00:00"
}
```

#### troubleshooting

You need to do a aws login sso and to refresh your credentials and then update them in the 
./awas/credentials file.
