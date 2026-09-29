
# Estructuras de Datos — Listas, Pilas y Colas en Java

Proyecto académico para la tarea **Implementación y Análisis de Complejidad de Listas, Pilas y Colas en Java**.

## Estructuras implementadas
- Lista simplemente enlazada sin cola.
- Lista simplemente enlazada con cola.
- Lista doblemente enlazada sin cola.
- Lista doblemente enlazada con cola.
- `MyStack<T>` mediante arreglo circular dinámico.
- `MyQueue<T>` mediante arreglo circular dinámico.

No se utilizan colecciones Java para implementar las estructuras. Las librerías estándar se usan únicamente para utilidades del benchmark (`java.time`/`java.util`) y E/S.

## Compilación
```bash
javac -d out src/*.java
```

## Prueba funcional
```bash
java -cp out Main
```

## Benchmark
```bash
java -cp out BenchmarkMain results/benchmark.csv
```

El benchmark usa `System.nanoTime()`, 3 repeticiones y mediana. La graficación se realiza posteriormente en Python para no contaminar la medición.


