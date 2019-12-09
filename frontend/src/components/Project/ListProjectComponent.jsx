import React, { Component } from 'react'
import ProjectService from "../Service/ProjectService";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Button from '@material-ui/core/Button';
import CreateIcon from '@material-ui/icons/Create';
import DeleteIcon from '@material-ui/icons/Delete';
import Typography from '@material-ui/core/Typography';
import Link from "@material-ui/core/Link";
import withStyles from "@material-ui/core/styles/withStyles";
import Paper from "@material-ui/core/Paper";
import DateFormatter from '../helper/DateFormatter'
const StyledTableCell = withStyles(theme => ({
    head: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    body: {
        fontSize: 14,
    },
}))(TableCell);

const StyledTableRow = withStyles(theme => ({
    root: {
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.background.default,
        },
    },
}))(TableRow);
class ListProjectComponent extends Component {

    constructor(props) {
        super(props)
        this.state = {
            projects: [],
            columns: [
                { title: 'სახელი', field: 'name' },
                { title: 'პროექტის ტიპი', field: 'projectType' },
                { title: 'დაწყების დრო', field: 'startYear', type: 'date' },
                { title: 'დამთავრების დრო', field: 'endYear', type: 'date' },
            ]
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
        this.props.history.push('/edit-Project/'+id);
    }


    render() {
        return (
            <div>
                <Paper >
                    <Typography variant="h4" style={style}>პროექტები</Typography>
                </Paper>
                <Table>
                    <TableHead >
                        <StyledTableRow >
                            <StyledTableCell align="left">სახელი</StyledTableCell>
                            <StyledTableCell align="left">პროექტის ტიპი</StyledTableCell>
                            <StyledTableCell align="left">დაწყების დრო</StyledTableCell>
                            <StyledTableCell align="left">დამთავრების დრო</StyledTableCell>
                            <StyledTableCell>
                                <Button variant="contained" color="primary" href="/add-project">
                                    პროექტის შექმნა
                                </Button>
                            </StyledTableCell>

                        </StyledTableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.projects.map(row => (
                            <StyledTableRow key={row.projectId}>
                                <StyledTableCell align="left"><Link href={"/projects/"+row.projectId} color={"inherit"} style={{textDecoration:"none"}}><Typography>{row.name}</Typography></Link></StyledTableCell>
                                <StyledTableCell align="left"><Typography>{currencies.find(s=>s.value===row.projectType).label}</Typography></StyledTableCell>
                                <StyledTableCell align="left"><Typography>{DateFormatter.convertToString(row.startYear)}</Typography></StyledTableCell>
                                <StyledTableCell align="left"><Typography>{DateFormatter.convertToString(row.endYear)}</Typography></StyledTableCell>
                                <StyledTableCell align="left">
                                    <Link href={"edit-Project/"+row.projectId} color={"inherit"} style={{textDecoration:"none"}}><CreateIcon /></Link>
                                    <DeleteIcon onClick={() => this.deleteProject(row.projectId)}/>
                                </StyledTableCell>
                            </StyledTableRow>
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