class CreateAccounts < ActiveRecord::Migration
	def change
		create_table :accounts do |t|
			t.string :number
			t.integer :balance
		end
	end
end