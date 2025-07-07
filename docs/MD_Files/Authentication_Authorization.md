## Authentication & Authorization

The system uses **JWT tokens** for secure login. Tokens are signed using **asymmetric RSA keys** and validated by the application.

### JWT Setup

- **Algorithm**: RS256
- **Signing Key**: `privateKey.pem` (located in `src/main/resources/keys`)
- **Verification Key**: `publicKey.pem` (located in `src/main/resources/keys`)
- **Issuer**: `helpdesk-system`
- **Token lifespan**: 1 hour

### Token Generation Endpoint

- `POST /auth/login`  
  Accepts JSON with `username` and `password`:
  ````json
  {
    "username": "csupport1",
    "password": "securePass123"
  }
  ````
  Returns:
  ````json
  {
    "token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
  ````

### Authorization

Two endpoints currently demonstrate role-based access via `@RolesAllowed`:
- `@RolesAllowed({"ADMIN", "CUSTOMERSUPPORT"})` on POST `/actions/communication`
- `@RolesAllowed({"ADMIN",TECHNICIAN"})` on POST `/actions/technical`

Other endpoints are open by default (not protected), to allow easy testing and demonstration.

## Users

The application supports two main roles with hardcoded demo users (from `import.sql`):

| Username  | Password      | Role            |
|-----------|---------------|-----------------|
| csupport1 | securePass123 | CUSTOMERSUPPORT |
| tech1     | passTech123   | TECHNICIAN      |
| admin1    | adminPass     | ADMIN           |