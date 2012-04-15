require 'sinatra'
require 'json'
class FruitApp < Sinatra::Base
  set :data do 
  	JSON.parse(File.read('fruits.json'))
  end
  get '/fruits' do
  	content_type :json
  	FruitApp.data.to_json
  end
end