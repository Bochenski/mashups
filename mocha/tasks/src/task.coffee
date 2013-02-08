class Task
  constructor: (@name) ->
    @status = 'incomplete'
  complete: ->
    @status = 'complete'
    true

root = exports ? window
root.Task = Task