# Architecture

- Java 21
- Spring boot
- Maven
- Hexagonal architecture
- Mongo (embedded for this project)
- Document versioning (to prevent concurrent updates)

# Notes

- Not all code is covered by tests. But should be enough to demonstrate my style of testing
- Add validation, e.q. non null dto fields, dates should be in future, at least one guest should be provided 