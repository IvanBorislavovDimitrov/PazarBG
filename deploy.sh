
cf d pazar-bg -f
cf ds pazar_bg_db -f
cf cs elephantsql turtle pazar_bg_db
cf push -f manifest.yml
cf map-route pazar-bg cfapps.io --hostname pazar-ivan
