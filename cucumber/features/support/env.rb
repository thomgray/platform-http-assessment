require 'webmock/cucumber'
require 'rest-client'
require 'pry'
require 'json'
require 'rspec/expectations'

PROJECT_DIR = File.expand_path('../../..', __dir__)

puts "project dir = #{PROJECT_DIR}"
EXECUTABLE = File.join(PROJECT_DIR, 'target/platform-http-assessment-1.0.0-shaded.jar')
puts "executable = #{EXECUTABLE}"
