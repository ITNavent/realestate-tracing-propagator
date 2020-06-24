# realestate-tracing-propagator

![Jaeger tracing](https://www.nuget.org/profiles/JaegerTracing/avatar?imageSize=256)

## Uso

El tracing distribuido es uno de los beneficio de utilizar Istio, ya que con solo agregar unos encabezados podemos tomar los tiempos de los requests y resposes entre aplicaciones.

Para poder seguir el movimiento de un request a traves de las apps es necesitario agregar los siguentes encabezados:

* x-request-id
* x-b3-traceid
* x-b3-spanid
* x-b3-parentspanid
* x-b3-sampled
* x-b3-flags
* x-ot-span-context


Esta libreria se encarga de esta tarea generando un interceptor de Spring que los almacena en el org.slf4j.MDC y luego permite enviarlos en las llamadas salientes de la app.

```gradle
compile 'com.navent.realestate:realestate-tracing-propagator:0.0.6'
```

## Configuración

### Para el interceptor, llamadas entrantes:

```java
@Bean
public RequestTracerInterceptor requestTracerInterceptor() {
	return new RequestTracerInterceptor();
}
```

```java
@Override
public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(requestTracerInterceptor());
}
```

### Para la invocacion a servicios, llamadas salientes:

##### Feign

Sumar el interceptor:

```java
public Feign.Builder feignBuilder() {
	return Feign.builder()
		.requestInterceptor(new B3HeadersPropagator())
}
```

##### Otros clientes http

Usar el servicio:

```java
private B3HeadersService b3HeadersService;
```

```java
webclient.headers(c -> c.addAll(b3HeadersService.mdcHeadersToMultiValueMap(MDC.getMDCAdapter())))
```

## Visualización de trazas.

[Tracing prod](http://tracing-prd.core.re.navent.biz/jaeger/search)

[Tracing staging](http://tracing-stg.core.re.navent.biz/jaeger/search)
