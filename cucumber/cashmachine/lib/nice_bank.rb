require 'sinatra'
require_relative 'transaction_queue'
require_relative 'balance_store'

class Account
	def initialize
		@queue = TransactionQueue.new
		@balance_store = BalanceStore.new
	end
	def balance
		@balance_store.balance
	end
	def credit(amount)
		@queue.write("+#{amount}")
	end
	def debit(amount)
		@queue.write("-#{amount}")
	end
end

class Teller
	def initialize(cash_slot)
		@cash_slot = cash_slot
	end

	def withdraw_from(account,amount)
		account.debit(amount)
		@cash_slot.dispense(amount)
	end
end

class CashSlot
	def contents
		@contents or raise("I'm empty")
	end
	def dispense(amount)
		@contents = amount
	end
end

get '/' do 
	%{
		<html>
			<body>
				<form action="/withdraw" method="post">
					<label for="amount">Amount</label>
					<input type="text" id="amount" name="amount">
					<button type="submit">Withdraw</button>
				</form>
			</body>
		</html>
	}
end

set :cash_slot, CashSlot.new
set :account do
	fail 'account has not been set'
end
post '/withdraw' do 
	teller = Teller.new(settings.cash_slot)
	teller.withdraw_from(settings.account,params[:amount].to_i)
end

