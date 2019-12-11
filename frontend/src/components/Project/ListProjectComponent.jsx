import React, {Component} from 'react'
import ProjectService from "../Service/ProjectService";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import CreateIcon from '@material-ui/icons/Create';
import DeleteIcon from '@material-ui/icons/Delete';
import Typography from '@material-ui/core/Typography';
import Link from "@material-ui/core/Link";
import AddCircleIcon from '@material-ui/icons/AddCircle';
import Paper from "@material-ui/core/Paper";
import DateFormatter from '../helper/DateFormatter'


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
        this.props.history.push('/edit-Project/' + id);
    }


    render() {
        return (
            <Paper elevation={10} style={{marginBottom: 30}}>
                <br/>
                <Typography variant="h4" style={style}>პროექტები</Typography>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell align="left">სახელი</TableCell>
                            <TableCell align="left">პროექტის ტიპი</TableCell>
                            <TableCell align="left">დაწყების დრო</TableCell>
                            <TableCell align="left">დამთავრების დრო</TableCell>
                            <TableCell>
                                <Link href={"/add-project"}> <AddCircleIcon fontSize={"large"} color="inherit" />
                                </Link>
                            </TableCell>

                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.projects.map(row => (
                            <TableRow key={row.projectId}>
                                <TableCell align="left"><Link href={"/projects/" + row.projectId}
                                                              color={"inherit"}
                                                              style={{textDecoration: "none"}}><Typography>{row.name}</Typography></Link></TableCell>
                                <TableCell
                                    align="left"><Typography>{currencies.find(s => s.value === row.projectType).label}</Typography></TableCell>
                                <TableCell
                                    align="left"><Typography>{DateFormatter.convertToString(row.startYear)}</Typography></TableCell>
                                <TableCell
                                    align="left"><Typography>{DateFormatter.convertToString(row.endYear)}</Typography></TableCell>
                                <TableCell >
                                    <Link href={"edit-Project/" + row.projectId} color={"inherit"}
                                          style={{textDecoration: "none"}}><CreateIcon/></Link>
                                    <DeleteIcon onClick={() => this.deleteProject(row.projectId)}/>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Paper>
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
const style = {
    display: 'flex',
    justifyContent: 'center'
}

export default ListProjectComponent;