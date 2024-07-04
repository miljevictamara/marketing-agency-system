# bsep-ra-2024-kt2-tim-1

RA 37/2020 Anđela Đorđević

RA 48/2020 Nina Batranović

RA 188/2020 Tamara Miljević

RA 210/2020 Adrian Antonić


# System Purpose

As part of the project task, it is necessary to implement an information system that keeps records of clients and employees in a marketing agency. The information system should include services for working with users (employees and clients), a service for monitoring events in the system, and a PKI service for certificate management. Access to the system is granted to employees, clients, and system administrators. The main purpose of the application is to maintain records of employees, currently active and previous clients, as well as all security-relevant events within the system.

# Marketing agency roles
Client
Employee
Admin
Unauthenticated users

# System Functionalities

User Registration: Users can register by providing email, password, name, address details, and type (individual or legal entity). For legal entities, business name and registration ID are required.

Client Registration Approval: Administrators review and approve or reject registrations. Approved clients receive an activation link via email.

Password and Passwordless Login: Users can log in with email and password or opt for passwordless login. Access and refresh tokens are generated upon successful login.

Two-Factor Authentication: Users can enable two-factor authentication using TOTP for added security.

CAPTCHA: Implemented for login security, requiring users to solve simple puzzles.

Access Control: Role-based access control (RBAC) ensures authorized access to system resources.

Data Encryption: Sensitive data is encrypted before storage, adhering to GDPR guidelines.

User Management: Admins manage users, including blocking and password resets.

Logging and Monitoring: Comprehensive logging and real-time event monitoring ensure system security and integrity.

HTTPS: Secure communication between client and server using HTTPS protocol.

# Screenshots
![Screenshot (113)](https://github.com/Batranovic/marketing-agency/assets/117094666/d8ff99c2-1341-4c84-ba67-9fd2de45b672)

![Screenshot (103)](https://github.com/Batranovic/marketing-agency/assets/117094666/48601714-5d67-4f73-8ec1-425e289e1548)

![Screenshot (105)](https://github.com/Batranovic/marketing-agency/assets/117094666/a6dd1a5b-8623-471d-aa48-eb8e8ea9cf18)

![Screenshot (106)](https://github.com/Batranovic/marketing-agency/assets/117094666/9a01ec79-a140-47ab-a664-ac9e6a4dadce)

![Screenshot (107)](https://github.com/Batranovic/marketing-agency/assets/117094666/32f721c2-a88c-475e-bab8-7cf9005899fd

![Screenshot (108)](https://github.com/Batranovic/marketing-agency/assets/117094666/a3d303d7-06ad-44d2-81f6-7d76f24e3e71)

![Screenshot (109)](https://github.com/Batranovic/marketing-agency/assets/117094666/75760407-9089-4cc2-9958-7783e208d733)

![Screenshot (111)](https://github.com/Batranovic/marketing-agency/assets/117094666/86fdd08f-bd58-456d-8325-6013b6a6d467)

![Screenshot (112)](https://github.com/Batranovic/marketing-agency/assets/117094666/88e2d916-9d61-413f-b110-516eebfd7a79)

![Screenshot (110)](https://github.com/Batranovic/marketing-agency/assets/117094666/50acca82-b01d-45d5-ad71-fc3149323c4b)
