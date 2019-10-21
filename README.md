# Culture code
Prototype of universal recomendation system

You can see REST by [HAL browser](http://localhost:8080/browser/index.html#/).
Recommendations rest-controller accessed by [/recommendations](http://localhost:8080/recommendations/) path.

You must to set environment parameters:
- githubClientSecret - client secret for github auth;
- googleClientSecret - clitne secret for google auth;
- tokenSecret - tocken secret for protect client-server exchange.

And if you want, you can define environment parameters:
- culturecode.db.host - mongodb host (localhost by default);
- culturecode.db.port - mongodb port (27017 by default);
- culturecode.db.database - mongodb database name (culture-code by default).

#### Users by default:
- Admin with password 111111
- User with password 111111

### Monolith is a first attempt to make it works
