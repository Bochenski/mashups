package com.gintellect.stocksystem.snippet

import _root_.net.liftweb.common._
import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util.Helpers._
import _root_.net.liftweb.sitemap._
import _root_.scala.xml._
import _root_.net.liftweb.http.S._
import _root_.net.liftweb.http.RequestVar
import _root_.net.liftweb.util.Helpers._
import _root_.net.liftweb.common.Full
import com.gintellect.stocksystem.model.Pet
import net.liftweb.mongodb.{ Skip, Limit }
import _root_.net.liftweb.http.S._
import _root_.net.liftweb.mapper.view._
import com.mongodb._

class PetSnippet extends StatefulSnippet with PaginatorSnippet[Pet] {

  var dispatch: DispatchIt = {
    case "showAll" => showAll _
    case "editForm" => editForm _
    case "paginate" => paginate _
  }

  var editingPet = Pet.createRecord

  def showAll(xhtml: NodeSeq): NodeSeq = {
    page.flatMap(pet => {
      (".petEdit *" #> link("pet/edit", () => edit(pet), Text("Edit")) &
        ".petDelete *" #> link("", () => delete(pet), Text("Delete")) &
        ".petName *" #> pet.name &
        ".petAge *" #> pet.age).apply(xhtml)
    })
  }

  def editForm(xhtml: NodeSeq): NodeSeq = {
    ("#editName" #> editingPet.name.toForm &
      "#editAge" #> editingPet.age.toForm &
      "#editDescription" #> editingPet.description.toForm &
      "type=submit" #> SHtml.submit(?("Save"), () => save)).apply(xhtml)
  }

  override def count = Pet.count
  override def itemsPerPage = 5
  override def page = Pet.findAll(QueryBuilder.start().get(), Limit(itemsPerPage), Skip(curPage * itemsPerPage))

  def edit(pet: Pet) = {
    editingPet = pet
  }

  def delete(pet: Pet) = {
    pet.delete_!
    redirectToHome
  }

  def save = {
    editingPet.save
    redirectToHome
  }

  def redirectToHome = {
    editingPet = Pet.createRecord
    redirectTo("/pet")
  }

}