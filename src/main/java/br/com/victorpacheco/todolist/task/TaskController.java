package br.com.victorpacheco.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.victorpacheco.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    //POST to Save Task to a user
    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        //Obtem da requisição recebida o UUID do usuário
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        //Valida se data início e fim são menores que a data atual do sistema
        var currentDate = LocalDateTime.now();        
        if (currentDate.isAfter(taskModel.getStarAt()) || currentDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de início / fim deve ser maior do que a data atual.");
        }

        //Valida se data fim é menor que a data início
        if (taskModel.getStarAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de fim deve ser maior do que a data de início.");
        }

        //Salva os dados recebidos na requisisão e retorna todos os dados na response 
        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    //GET to Taks List created for especific user
    @GetMapping("/")    
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    //PUT to Update based on ID Task
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        //Pega o ID recebido da task
        var task = this.taskRepository.findById(id).orElse(null);

        //Se task não encontrada retorna erro
        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada.");
        }

        var idUser = request.getAttribute("idUser");

        //Se usuário diferente do que criou a task retorna erro
        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ususário não tem permissão para alterar essa tarefa.");
        }

        //Busca parâmetros não recebidos para complementar a atualização
        Utils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }
}
