require_relative 'transaction_queue'
require_relative 'balance_store'
transaction_queue = TransactionQueue.new
balance_store = BalanceStore.new
puts "transaction processor ready"
loop do
	transaction_queue.read do |message|
		transaction_amount = message.to_i
		new_balance = balance_store.balance + transaction_amount
		balance_store.balance = new_balance
	end
end