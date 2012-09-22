function p = predict(Theta1, Theta2, X)
%PREDICT Predict the label of an input given a trained neural network
%   p = PREDICT(Theta1, Theta2, X) outputs the predicted label of X given the
%   trained weights of a neural network (Theta1, Theta2)

% Useful values
m = size(X, 1);
num_labels = size(Theta2, 1);

% You need to return the following variables correctly 
p = zeros(size(X, 1), 1);

% ====================== YOUR CODE HERE ======================
% Instructions: Complete the following code to make predictions using
%               your learned neural network. You should set p to a 
%               vector containing labels between 1 to num_labels.
%
% Hint: The max function might come in useful. In particular, the max
%       function can also return the index of the max element, for more
%       information see 'help max'. If your examples are in rows, then, you
%       can use max(A, [], 2) to obtain the max for each row.
%


% Add ones to the X data matrix
X = [ones(m, 1) X];

% calculate z of row 2 by multiplying by Theta1 transpose 
Z2 = X * Theta1';

% calculate sigmoid elementwise
A2 = sigmoid(Z2);

% Add ones to the A2 data matrix
n = size(A2,1);
A2 = [ones(n,1) A2];

% forward propagate to the next level
Z3 = A2 * Theta2';
A3 = sigmoid(Z3);

%finally work out the maximum and return its index
[z,p] = max(A3, [] , 2);

% =========================================================================


end
