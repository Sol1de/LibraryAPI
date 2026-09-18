.DEFAULT_GOAL := help

SEEDER_NAMES := MainSeeder AuthorSeeder ReaderSeeder LibrarySeeder BookShelfSeeder ShelfSeeder BookSeeder
DEFAULT_SEEDER := MainSeeder

.PHONY: help up db db\:update db\:seed db\:drop run shell build down

help:
	@printf '%s\n' \
		'make up                    : start Docker Compose in the background' \
		'make db                    : start only the database in the background' \
		'make db:update             : update database tables from JPA models' \
		'make db:seed <seeder name> : seed the database' \
		'make db:drop               : DELETE ALL user tables and their data in the configured Docker database' \
		'make run                   : run the application without Docker' \
		'make shell                 : open Bash or Sh in the app container' \
		'make build                 : build Docker Compose images' \
		'make down                  : stop Docker Compose without deleting volumes'

up:
	docker compose up -d

db:
	docker compose up -d database

db\:update:
	docker compose up -d --wait database
	sh mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.main.web-application-type=none --spring.jpa.hibernate.ddl-auto=update --seed.enabled=false --library.command=true"

db\:seed: SELECTED_SEEDER = $(if $(filter db:seed,$(firstword $(MAKECMDGOALS))),$(or $(word 2,$(MAKECMDGOALS)),$(DEFAULT_SEEDER)),$(DEFAULT_SEEDER))
db\:seed:
	$(if $(and $(filter db:seed,$(firstword $(MAKECMDGOALS))),$(word 3,$(MAKECMDGOALS))),$(error Usage: make db:seed [seeder name]))
	$(if $(filter $(SELECTED_SEEDER),$(SEEDER_NAMES)),,$(error Unknown seeder. Available: $(SEEDER_NAMES)))
	docker compose up -d --wait database
	sh mvnw spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.arguments="--spring.main.web-application-type=none --spring.jpa.hibernate.ddl-auto=update --seed.enabled=true --seed.name=$(SELECTED_SEEDER) --library.command=true"

# Accept the seeder name as an extra target only for db:seed.
.DEFAULT:
	@$(if $(and $(filter db:seed,$(firstword $(MAKECMDGOALS))),$(filter $@,$(SEEDER_NAMES))),:,$(error Unknown target: $@))

db\:drop:
	docker compose up -d --wait database
	@printf '%s\n' 'BEGIN;' \
		"SELECT format('DROP TABLE IF EXISTS %I.%I CASCADE;', schemaname, tablename) FROM pg_tables WHERE schemaname <> 'information_schema' AND schemaname !~ '^pg_';" \
		'\gexec' 'COMMIT;' | \
		docker compose exec -T database sh -c 'exec psql -X -v ON_ERROR_STOP=1 -U "$$POSTGRES_USER" -d "$$POSTGRES_DB"'

run:
	sh mvnw spring-boot:run

shell:
	docker compose exec app sh -c 'if command -v bash >/dev/null 2>&1; then exec bash; else exec sh; fi'

build:
	docker compose build

down:
	docker compose down
