package cn.edu.seig.novel.dao.mapper;

import cn.edu.seig.novel.dao.entity.BookInfo;
import cn.edu.seig.novel.dto.BookSearchReqDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BookInfoMapper extends BaseMapper<BookInfo> {

    void addVisitCount(@Param("bookId") Long bookId);

    List<BookInfo> searchBooks(IPage<BookInfo> page, BookSearchReqDto condition);

}
