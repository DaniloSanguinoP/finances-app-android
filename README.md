# Arquitectura MVVM Implementada

## Capas de la Aplicación
- **Presentation Layer**: Composables, ViewModels, Estados UI  
- **Domain Layer**: Use Cases, Repository Interface  
- **Data Layer**: API Service, Repository Implementation  
- **DI Layer**: Módulos de Hilt para inyección de dependencias  

---

## ⚙️ Funcionalidades Implementadas

### ✅ Requisitos Funcionales Cumplidos
**Resumen Financiero (parte superior):**
- Saldo Total del campo **balance**  
- Ingresos Totales (suma de transacciones tipo *ingreso*)  
- Egresos Totales (suma absoluta de transacciones tipo *egreso*)  

**Lista de Transacciones:**
- Descripción, fecha y monto de cada transacción  
- Colores dinámicos: **Verde (ingresos)** / **Rojo (egresos)**  

**Estados de UI:**
- **Loading**: Indicador de carga  
- **Error**: Mensaje de error con botón *Reintentar*  
- **Success**: Visualización de datos  

---

## Testing Incluido
- Tests unitarios para `CalculateTransactionSumsUseCase` y `ListTransactionViewModel`
- Pruebas para suma de ingresos y egresos  
- Casos edge como listas vacías y tipos *case-insensitive*  

---

## Tecnologías Utilizadas
- **Jetpack Compose** + Material 3  
- **Hilt** para inyección de dependencias  
- **Retrofit** + Kotlin Serialization para API  
- **StateFlow** para manejo reactivo de estados  
- **Coroutines** para programación asíncrona  

---

## Estructura del Proyecto

```plaintext
app/src/main/java/com/example/appBanco/
├── data/           # API, Repository Implementation, Models
├── domain/         # Use Cases, Repository Interface
├── presentation/   # UI, ViewModels, States
├── di/             # Dependency Injection
└── ui/theme/       # Material Theme
```
---

## Ejecutar proyecto:

1. Descargar el repositorio
2. Sincronizar dependencias
3. Ejecutar en emulador/dispositivo
