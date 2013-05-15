#
# Cookbook Name:: test-cookbook
# Recipe:: default
#
# Copyright (C) 2013 David Bochenski
# 
# All rights reserved - Do Not Redistribute
#
node.default['nodejs']['version'] = '0.8.12'
include_recipe "nodejs"