
graph TB
    Client((Client))
    
    subgraph API_User["API User Service V1"]
        UserAPI[Spring WebFlux API]
        Security[JWT Security]
        Cache[Redis Cache]
    end
    
    subgraph API_Data["API Data Service V1"]
        DataAPI[Spring WebFlux API]
        Database[(H2 Database)]
    end
    
    Client -->|HTTP Request| UserAPI
    UserAPI -->|Security| Security
    UserAPI -->|Cache Session| Cache
    UserAPI -->|Forward Request| DataAPI
    DataAPI -->|CRUD Operations| Database
    
    style Client fill:#f9f,stroke:#333,stroke-width:4px
    style API_User fill:#bbf,stroke:#333,stroke-width:2px
    style API_Data fill:#bfb,stroke:#333,stroke-width:2px
    
    classDef service fill:#ddd,stroke:#333,stroke-width:2px
    class UserAPI,DataAPI,Security,Cache service