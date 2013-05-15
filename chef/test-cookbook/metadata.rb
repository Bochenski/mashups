name             "test-cookbook"
maintainer       "David Bochenski"
maintainer_email "david@gintellect.com"
license          "All rights reserved"
description      "Installs/Configures test-cookbook"
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          "0.1.0"

depends 'nginx'
depends 'apt'
depends 'mongodb'
depends 'nodejs'
