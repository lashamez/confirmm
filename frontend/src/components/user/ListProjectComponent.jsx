import React, { Component } from 'react'
import ProjectService from "../service/ProjectService";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Button from '@material-ui/core/Button';
import CreateIcon from '@material-ui/icons/Create';
import DeleteIcon from '@material-ui/icons/Delete';
import Typography from '@material-ui/core/Typography';
import { Link } from 'react-router-dom';

class ListProjectComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            projects: [],
        }
        this.deleteProject = this.deleteProject.bind(this);
        this.editProject = this.editProject.bind(this);
        this.reloadProjectList = this.reloadProjectList.bind(this);
    }

    componentDidMount() {
        this.reloadProjectList();
    }

    reloadProjectList() {
        ProjectService.fetchProjects()
            .then((res) => {
                console.log(res.data)
                this.setState({projects: res.data})
            });
    }

    deleteProject(projectId) {
        ProjectService.deleteProject(projectId)
            .then(res => {
                this.setState({projects: this.state.projects.filter(project => project.projectId !== projectId)});
            })
    }

    editProject(id) {
        this.props.history.push('/edit-project/'+id);
    }


    render() {
        return (
            <div>
                <Typography variant="h4" style={style}>პროექტები</Typography>
                <Button variant="contained" color="primary" href="/add-project">
                    პროექტის შექმნა
                </Button>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell align="left">სახელი</TableCell>
                            <TableCell align="left">პროექტის ტიპი</TableCell>
                            <TableCell align="left">დაწყების დრო</TableCell>
                            <TableCell align="left">დამთავრების დრო</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.projects.map(row => (
                            <TableRow key={row.projectId}>
                                <TableCell align="left"><Link to={"/projectDetails/"+row.projectId}>{row.name}</Link></TableCell>
                                <TableCell align="left">{currencies.find(s=>s.value===row.projectType).label}</TableCell>
                                <TableCell align="left">{row.startYear}</TableCell>
                                <TableCell align="left">{row.endYear}</TableCell>
                                <TableCell align="left"><Link to={"edit-project/"+row.projectId}><CreateIcon /></Link> </TableCell>
                                <TableCell align="left" onClick={() => this.deleteProject(row.projectId)}><DeleteIcon /></TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>

            </div>
        );
    }
}
const currencies = [
    {
        value: 'large',
        label: 'დიდი',
    },
    {
        value: 'medium',
        label: 'საშუალო',
    },
    {
        value: 'small',
        label: 'პატარა',
    }
];
const style ={
    display: 'flex',
    justifyContent: 'center'
}

export default ListProjectComponent;