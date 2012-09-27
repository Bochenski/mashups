function [J grad] = nnCostFunction(nn_params, ...
                                   input_layer_size, ...
                                   hidden_layer_size, ...
                                   num_labels, ...
                                   X, y, lambda)
%NNCOSTFUNCTION Implements the neural network cost function for a two layer
%neural network which performs classification
%   [J grad] = NNCOSTFUNCTON(nn_params, hidden_layer_size, num_labels, ...
%   X, y, lambda) computes the cost and gradient of the neural network. The
%   parameters for the neural network are "unrolled" into the vector
%   nn_params and need to be converted back into the weight matrices. 
% 
%   The returned parameter grad should be a "unrolled" vector of the
%   partial derivatives of the neural network.
%

% Reshape nn_params back into the parameters Theta1 and Theta2, the weight matrices
% for our 2 layer neural network
Theta1 = reshape(nn_params(1:hidden_layer_size * (input_layer_size + 1)), ...
                 hidden_layer_size, (input_layer_size + 1));

Theta2 = reshape(nn_params((1 + (hidden_layer_size * (input_layer_size + 1))):end), ...
                 num_labels, (hidden_layer_size + 1));

% Setup some useful variables
m = size(X, 1);
         
% You need to return the following variables correctly 
J = 0;
Theta1_grad = zeros(size(Theta1));
Theta2_grad = zeros(size(Theta2));

% ====================== YOUR CODE HERE ======================
% Instructions: You should complete the code by working through the
%               following parts.
%
% Part 1: Feedforward the neural network and return the cost in the
%         variable J. After implementing Part 1, you can verify that your
%         cost function computation is correct by verifying the cost
%         computed in ex4.m
%
% Part 2: Implement the backpropagation algorithm to compute the gradients
%         Theta1_grad and Theta2_grad. You should return the partial derivatives of
%         the cost function with respect to Theta1 and Theta2 in Theta1_grad and
%         Theta2_grad, respectively. After implementing Part 2, you can check
%         that your implementation is correct by running checkNNGradients
%
%         Note: The vector y passed into the function is a vector of labels
%               containing values from 1..K. You need to map this vector into a 
%               binary vector of 1's and 0's to be used with the neural network
%               cost function.
%
%         Hint: We recommend implementing backpropagation using a for-loop
%               over the training examples if you are implementing it for the 
%               first time.
%
% Part 3: Implement regularization with the cost function and gradients.
%
%         Hint: You can implement this around the code for
%               backpropagation. That is, you can compute the gradients for
%               the regularization separately and then add them to Theta1_grad
%               and Theta2_grad from Part 2.
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

% A3 is now an m (number of examples) by n (number of categories) matrix (i.e. weve completed forward propagation)

% need to craete Y with 1s in the locations of the relevant digits( zeros elsewhere ) so that it is 5000 x 10
Y = zeros(m, num_labels);

for i = 1 : num_labels
	for j = 1 : m
		if(y(j) == i)
			Y(j,i) = 1;
		endif
	endfor
endfor

% crude non-vectorised implemnetation of the cost function, but I seem to recall this was deemed acceptable 
% from the lecture videos

for i = 1 : m
	for k = 1  : num_labels
		J = J + (  -Y(i,k) * log(A3(i,k)) - (1 - Y(i,k)) * log(1 -  A3(i,k)));
	endfor
endfor

% next divide the sum by the number of training examples
J = J/m;

% step 2 - calculate the regularization part
J = J + (sum(Theta1(:,2:end)(:).^2) + sum(Theta2(:,2:end)(:).^2)) * ( lambda / (2*m) );

%forgot to take off the ones at the beginning of each matrix :-(

for i = 1 : m
	%sizeXi = size(X(i,:))
	a1 = X(i, :);
	Z2 = a1 * Theta1';
	A2 = sigmoid(Z2);
	n = size(A2,1);
	A2 = [ones(n,1) A2];
	Z3 = A2 * Theta2';
	A3 = sigmoid(Z3);
	d3 = A3 - Y(i,:);
	d2 = (Theta2' * d3')(2:end, :) .* sigmoidGradient(Z2');
	Theta2_grad = Theta2_grad .+ ( d3' * A2 );
	Theta1_grad = Theta1_grad .+ ( d2 * a1 );
endfor

Theta2_grad = Theta2_grad ./ m;
Theta1_grad = Theta1_grad ./ m;

Theta2_reg = Theta2;
Theta2_reg(:, 1) = zeros(size(Theta2_reg,1),1);
Theta2_reg = Theta2_reg .* (lambda / m);
Theta2_grad = Theta2_grad + Theta2_reg;

Theta1_reg = Theta1;
Theta1_reg(:,1) = zeros(size(Theta1_reg,1),1);
Theta1_reg = Theta1_reg .* (lambda /m);
Theta1_grad = Theta1_grad + Theta1_reg; 

% -------------------------------------------------------------

% =========================================================================

% Unroll gradients
grad = [Theta1_grad(:) ; Theta2_grad(:)];


end
