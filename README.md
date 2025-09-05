¡Perfecto! 😎 Vamos a hacer un **README.md bonito**, con emojis, estructura de proyecto y guía rápida de uso. Aquí tienes una versión lista para copiar y pegar:

---

# 🧪 Java API Test Framework

Un framework ligero en **Java 17** para probar **REST**, **GraphQL** y **SOAP**, con **autenticación centralizada** (Bearer, Basic Auth y OAuth2).

💡 **Paquete base:** `org.example.apitest`

---

## 📁 Estructura del proyecto

```
apitest/
├─ pom.xml
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ org/example/apitest/
│  │  │     ├─ config/
│  │  │     │  └─ Config.java
│  │  │     ├─ http/
│  │  │     │  ├─ RequestSpec.java
│  │  │     │  ├─ ApiResponse.java
│  │  │     │  └─ Http.java
│  │  │     ├─ graphql/
│  │  │     │  └─ GraphQLClient.java
│  │  │     ├─ soap/
│  │  │     │  └─ SoapClient.java
│  │  │     └─ util/
│  │  │        └─ Json.java
│  │  └─ resources/
│  │     └─ application.yaml
│  └─ test/
│     ├─ java/
│     │  └─ org/example/apitest/
│     │     ├─ RestApiTest.java
│     │     ├─ GraphQLApiTest.java
│     │     └─ SoapApiTest.java
│     └─ resources/
│        └─ logback-test.xml
```

---

## ⚙️ Configuración

Archivo de configuración en `src/main/resources/application.yaml`:

```yaml
http:
  baseUrl: "https://httpbin.org"
  connectTimeoutMs: 5000
  readTimeoutMs: 15000
  writeTimeoutMs: 15000

graphql:
  endpoint: "https://countries.trevorblades.com/"

soap:
  endpoint: "https://www.dataaccess.com/webservicesserver/NumberConversion.wso"
```

---

## 🚀 Uso básico

### 1️⃣ Inicializar Http con autenticación

```java
Config cfg = new Config();
Http http = new Http(cfg);

// Bearer token estático
http.setBearerToken("MI_TOKEN");

// Basic Auth
// http.setBasicAuth("usuario","pass");

// OAuth2 dinámico
// http.setOAuthTokenSupplier(() -> obtenerTokenDinamico());
```

---

### 2️⃣ Usar en REST

```java
ApiResponse res = http.execute(RequestSpec.builder()
        .method(RequestSpec.Method.GET)
        .url("/get")
        .query("hello","world")
        .build());

System.out.println(res.json());
```

---

### 3️⃣ Usar en GraphQL

```java
GraphQLClient gql = new GraphQLClient(cfg, http);
String query = "query($code: ID!) { country(code: $code) { code name capital } }";
ApiResponse res = gql.execute(query, Map.of("code","ES"), "CountryQuery");
System.out.println(res.json());
```

---

### 4️⃣ Usar en SOAP

```java
SoapClient soap = new SoapClient(cfg, http);
String body = """
<NumberToWords xmlns="http://www.dataaccess.com/webservicesserver/">
    <ubiNum>123</ubiNum>
</NumberToWords>
""";

ApiResponse res = soap.call("\"http://www.dataaccess.com/webservicesserver/NumberConversion.wso/NumberToWords\"", body);
System.out.println(res.body());
```

---

## 🧩 Características

* ✅ Soporta **REST, GraphQL y SOAP**
* ✅ **Autenticación centralizada**: Bearer, Basic Auth y OAuth2
* ✅ JSON utilities con Jackson
* ✅ Configuración con YAML (`application.yaml`)
* ✅ Logging con SLF4J + Logback
* ✅ Tests con JUnit 5
* ✅ Fácil de extender a nuevos endpoints

---

## 📦 Ejecutar tests

```bash
mvn test
```

> Todos los tests usan el cliente HTTP centralizado con autenticación.

---

## ✨ Emojis clave

* 🧪 Framework de tests
* ⚙️ Configuración
* 🚀 Ejecución y ejemplos
* 🧩 Características
* 📦 Ejecución de tests

