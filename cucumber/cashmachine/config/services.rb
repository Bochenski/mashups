require "service_manager"
ServiceManager.define_service "transaction_processor" do |s|
	s.start_cmd = "ruby lib/transaction_processor.rb"
	s.loaded_cue = /ready/
	s.cwd = Dir.pwd
	s.pid_file = 'transaction_processor.pid'
end