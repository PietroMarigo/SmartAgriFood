#!/bin/bash

# Script per creare tutti i file per il progetto WMS
# Esecuzione: chmod +x create-java-files.sh && ./create-java-files.sh

echo "🚀 Creando struttura completa file Java..."

# AUTH MODULE (11 file)
touch src/main/java/com/warehouse/auth/entity/User.java
touch src/main/java/com/warehouse/auth/dto/LoginRequest.java
touch src/main/java/com/warehouse/auth/dto/LoginResponse.java
touch src/main/java/com/warehouse/auth/dto/UserRegisterRequest.java
touch src/main/java/com/warehouse/auth/dto/UserRegisterResponse.java
touch src/main/java/com/warehouse/auth/repository/UserRepository.java
touch src/main/java/com/warehouse/auth/exception/UserNotFoundException.java
touch src/main/java/com/warehouse/auth/exception/InvalidCredentialsException.java
touch src/main/java/com/warehouse/auth/exception/UserAlreadyExistsException.java
touch src/main/java/com/warehouse/auth/service/AuthService.java
touch src/main/java/com/warehouse/auth/controller/AuthController.java

# WAREHOUSE MODULE (15 file)
touch src/main/java/com/warehouse/warehouse/entity/ProductCode.java
touch src/main/java/com/warehouse/warehouse/entity/LocationCode.java
touch src/main/java/com/warehouse/warehouse/entity/Batch.java
touch src/main/java/com/warehouse/warehouse/dto/BarcodeInfo.java
touch src/main/java/com/warehouse/warehouse/dto/BatchReceiptRequest.java
touch src/main/java/com/warehouse/warehouse/dto/BatchReceiptResponse.java
touch src/main/java/com/warehouse/warehouse/repository/ProductCodeRepository.java
touch src/main/java/com/warehouse/warehouse/repository/LocationCodeRepository.java
touch src/main/java/com/warehouse/warehouse/repository/BatchRepository.java
touch src/main/java/com/warehouse/warehouse/exception/InvalidBarcodeException.java
touch src/main/java/com/warehouse/warehouse/exception/ProductNotFoundException.java
touch src/main/java/com/warehouse/warehouse/exception/LocationNotFoundException.java
touch src/main/java/com/warehouse/warehouse/service/BarcodeDecodingService.java
touch src/main/java/com/warehouse/warehouse/service/BatchService.java
touch src/main/java/com/warehouse/warehouse/controller/WarehouseController.java

# COMMON MODULE (6 file)
touch src/main/java/com/warehouse/common/dto/ApiResponse.java
touch src/main/java/com/warehouse/common/dto/ErrorResponse.java
touch src/main/java/com/warehouse/common/controller/.gitkeep
touch src/main/java/com/warehouse/common/exception/GlobalExceptionHandler.java
touch src/main/java/com/warehouse/common/repository/.gitkeep
touch src/main/java/com/warehouse/common/service/.gitkeep

# ORDER MODULE (10 file)
touch src/main/java/com/warehouse/order/entity/Order.java
touch src/main/java/com/warehouse/order/entity/OrderItem.java
touch src/main/java/com/warehouse/order/dto/CreateOrderRequest.java
touch src/main/java/com/warehouse/order/dto/OrderItemDTO.java
touch src/main/java/com/warehouse/order/dto/OrderResponse.java
touch src/main/java/com/warehouse/order/repository/OrderRepository.java
touch src/main/java/com/warehouse/order/exception/OrderNotFoundException.java
touch src/main/java/com/warehouse/order/exception/InvalidOrderException.java
touch src/main/java/com/warehouse/order/service/OrderService.java
touch src/main/java/com/warehouse/order/controller/OrderController.java

# TEST MODULE (6 file)
touch src/test/java/com/warehouse/auth/unit/.gitkeep
touch src/test/java/com/warehouse/auth/integration/.gitkeep
touch src/test/java/com/warehouse/warehouse/unit/.gitkeep
touch src/test/java/com/warehouse/warehouse/integration/.gitkeep
touch src/test/java/com/warehouse/order/unit/.gitkeep
touch src/test/java/com/warehouse/order/integration/.gitkeep

echo ""
echo "✅ Completato!"
echo ""
echo "📊 Riepilogo file creati:"
echo "   ✅ AUTH:      11 file"
echo "   ✅ WAREHOUSE: 15 file"
echo "   ✅ COMMON:     6 file"
echo "   ✅ ORDER:     10 file"
echo "   ✅ TEST:       6 placeholder"
echo ""
echo "📁 Totale:"
find src/main/java/com/warehouse src/test/java/com/warehouse -type f \( -name "*.java" -o -name ".gitkeep" \) | wc -l
