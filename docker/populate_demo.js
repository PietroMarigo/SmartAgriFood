const db = db.getSiblingDB('wms_db');

// Clear existing data
db.batches.deleteMany({});
db.orders.deleteMany({});
db.movements.deleteMany({});

const today = new Date("2026-02-01T10:00:00Z");

// 1. POPULATE BATCHES
// Format: SKU-Provider-Date-Increment
db.batches.insertMany([
  {
    "_id": "A001-002-20260101-001",
    "batchId": "A001-002-20260101-001",
    "productSku": "A001",
    "productName": "Mele Golden", // From data.sql
    "weightKg": 100.0,
    "expiryDate": new Date("2026-02-05T00:00:00Z"), // CRITICAL: Expires in 4 days
    "locationCode": "Z-A-001", // Format: Z-<Letter>-<Numbers>
    "status": "AVAILABLE",
    "provider": "002",
    "harvestDate": "20260101",
    "increment": "001",
    "timeStamp": today,
    "_class": "com.warehouse.common.entity.Batch" // For Spring Data mapping
  },
  {
    "_id": "A002-005-20260120-001",
    "batchId": "A002-005-20260120-001",
    "productSku": "A002",
    "productName": "Banane", // From data.sql
    "weightKg": 50.0,
    "expiryDate": new Date("2026-02-03T00:00:00Z"), // EXTREMELY CRITICAL: Expires in 2 days
    "locationCode": "Z-B-012",
    "status": "AVAILABLE",
    "provider": "005",
    "harvestDate": "20260120",
    "increment": "001",
    "timeStamp": today,
    "_class": "com.warehouse.common.entity.Batch"
  },
  {
    "_id": "A001-003-20260105-001",
    "batchId": "A001-003-20260105-001",
    "productSku": "A001",
    "productName": "Mele Golden",
    "weightKg": 150.0,
    "expiryDate": new Date("2026-02-20T00:00:00Z"), // FEFO Target: Expires sooner than the next one
    "locationCode": "Z-A-005",
    "status": "AVAILABLE",
    "provider": "003",
    "harvestDate": "20260105",
    "increment": "001",
    "timeStamp": today,
    "_class": "com.warehouse.common.entity.Batch"
  },
  {
    "_id": "A001-003-20260110-001",
    "batchId": "A001-003-20260110-001",
    "productSku": "A001",
    "productName": "Mele Golden",
    "weightKg": 200.0,
    "expiryDate": new Date("2026-03-01T00:00:00Z"), // FEFO Target: Expires later
    "locationCode": "Z-A-006",
    "status": "AVAILABLE",
    "provider": "003",
    "harvestDate": "20260110",
    "increment": "001",
    "timeStamp": today,
    "_class": "com.warehouse.common.entity.Batch"
  }
]);

// 2. POPULATE AN ORDER (For Warehouse UI demo)
db.orders.insertOne({
  "createdAt": today,
  "customer": "Ortofrutta Da Mario",
  "pickupDate": new Date("2026-02-03T00:00:00Z"),
  "status": "NOT_PROCESSED", // Matching OrderStatus enum
  "items": {
    "A001": {
      "A001-003-20260105-001": 25.0 // Manually allocated for demo
    }
  },
  "_class": "com.warehouse.common.entity.Order"
});

// 3. POPULATE A MOVEMENT (For audit trail demo)
db.movements.insertOne({
  "batchId": "A001-002-20260101-001",
  "oldLocation": "Z-A-000",
  "newLocation": "Z-A-001",
  "workerId": "MR001",
  "timeStamp": today,
  "_class": "com.warehouse.ware.entity.Movment" // Note the typo 'Movment' matches your file
});

print("Demo data successfully staged!");
