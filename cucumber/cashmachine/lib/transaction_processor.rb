require_relative 'transaction_queue'
require_relative 'account'
transaction_queue = TransactionQueue.new
puts "transaction processor ready"
loop do
	transaction_queue.read do |message|
		transaction_amount, number = message.split(/,/)
		account = Account.find_by_number!(number.strip)
		new_balance = account.balance + transaction_amount.to_i
		account.balance = new_balance
		account.save
	end
end