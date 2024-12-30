sequenceDiagram
    actor Client
    participant API_User as API User Service
    participant API_Data as API Data Service

    %% Invalid Email Format
    Client->>+API_User: POST /api/user-v1/user
    Note over API_User: Invalid Email Format
    API_User-->>-Client: 400 Bad Request

    %% Email Already Exists
    Client->>+API_User: POST /api/user-v1/user
    API_User->>+API_Data: POST /api/data-v1/user
    Note over API_Data: Email Already Exists
    API_Data-->>-API_User: 409 Conflict
    API_User-->>-Client: 409 Conflict

    %% Invalid Password
    Client->>+API_User: POST /api/user-v1/user
    Note over API_User: Invalid Password Pattern
    API_User-->>-Client: 400 Bad Request