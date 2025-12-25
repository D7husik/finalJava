# Environment Variables Setup

Before running the application, set these environment variables:

## Windows (PowerShell)
```powershell
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_actual_password"
$env:JWT_SECRET="your_secure_jwt_secret_key_at_least_32_characters"
```

## Windows (CMD)
```cmd
set DB_USERNAME=postgres
set DB_PASSWORD=your_actual_password
set JWT_SECRET=your_secure_jwt_secret_key_at_least_32_characters
```

## Linux/Mac
```bash
export DB_USERNAME=postgres
export DB_PASSWORD=your_actual_password
export JWT_SECRET=your_secure_jwt_secret_key_at_least_32_characters
```

## IntelliJ IDEA
1. Edit Run Configuration
2. Add Environment Variables:
   - DB_USERNAME=postgres
   - DB_PASSWORD=your_password
   - JWT_SECRET=your_secret

## Default Values (for development only)
If environment variables are not set, these defaults are used:
- DB_USERNAME: postgres
- DB_PASSWORD: changeme
- JWT_SECRET: (embedded default - not secure for production)

⚠️ NEVER commit your real passwords to Git!
