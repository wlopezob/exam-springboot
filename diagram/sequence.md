sequenceDiagram
    actor Client
    participant API_User as API User Service
    participant API_Data as API Data Service
    participant Redis as Redis Cache
    participant H2 as H2 Database

    Client->>+API_User: POST /api/user-v1/user
    Note over API_User: Validate Request
    Note over API_User: Validate Password Pattern
    Note over API_User: Generate JWT Token

    API_User->>+API_Data: POST /api/data-v1/user
    API_Data->>+H2: Save User Data
    H2-->>-API_Data: Return User ID
    API_Data->>+H2: Save Phone Data
    H2-->>-API_Data: Confirm Save
    API_Data->>+H2: Save Token
    H2-->>-API_Data: Confirm Save
    API_Data-->>-API_User: Return User Data Response

    API_User->>+Redis: Cache User Session
    Redis-->>-API_User: Confirm Cache
    API_User-->>-Client: Return User Response