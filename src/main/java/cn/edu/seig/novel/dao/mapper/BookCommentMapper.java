package cn.edu.seig.novel.dao.mapper;

import cn.edu.seig.novel.dao.entity.BookComment;
import cn.edu.seig.novel.dto.resp.CommentListItemRespDto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BookCommentMapper extends BaseMapper<BookComment> {

    /*
    分页联表查询评论信息，包括用户和小说信息
     */
    @Select("SELECT bc.id, bc.comment_content, bc.create_time, bc.user_id, bc.book_id, " +
            "u.username, b.book_name " +
            "FROM book_comment bc " +
            "LEFT JOIN user_info u ON bc.user_id = u.id " +
            "LEFT JOIN book_info b ON bc.book_id = b.id " +
            "${ew.customSqlSegment}")
    List<CommentListItemRespDto> listComments(
            IPage<CommentListItemRespDto> page, @Param("ew")QueryWrapper<CommentListItemRespDto> queryWrapper);

}
